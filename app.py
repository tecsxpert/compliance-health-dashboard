
from flask import Flask, request, jsonify
from services.chroma_client import ChromaService
from services.groq_client import GroqClient
import time

app = Flask(__name__)

chroma = ChromaService()
groq = GroqClient()

# ✅ TRACKING VARIABLES
START_TIME = time.time()
response_times = []
cache = {}
cache_hits = 0
cache_miss = 0


# ✅ EXISTING API (DO NOT CHANGE LOGIC — only added tracking)
@app.route("/query", methods=["POST"])
def query():
    global cache_hits, cache_miss

    start = time.time()

    data = request.json
    question = data.get("question")

    # ✅ Cache check
    if question in cache:
        cache_hits += 1
        answer = cache[question]
    else:
        cache_miss += 1

        results = chroma.query(question)
        docs = results["documents"][0]

        context = "\n".join(docs)

        prompt = f"""
Answer the question using the below data.

Data:
{context}

Question:
{question}
"""

        answer = groq.generate(prompt)

        # store in cache
        cache[question] = answer

    # ✅ Track response time
    elapsed = time.time() - start
    response_times.append(elapsed)

    if len(response_times) > 10:
        response_times.pop(0)

    return jsonify({
        "answer": answer
    })


# ✅ NEW API (FOR YOUR TEST)
@app.route("/categorise", methods=["POST"])
def categorise():
    data = request.json
    text = data.get("text")

    results = chroma.query(text)
    docs = results["documents"][0]

    context = "\n".join(docs)

    prompt = f"""
Classify the issue based on the given data.

Data:
{context}

User Input:
{text}

Return ONLY JSON like:
{{
  "category": "...",
  "confidence": 0.0,
  "reasoning": "..."
}}
"""

    answer = groq.generate(prompt)

    if isinstance(answer, str):
        return jsonify({
            "category": "Unknown",
            "confidence": 0.0,
            "reasoning": answer
        })

    return jsonify(answer)


# ✅ NEW HEALTH ENDPOINT
@app.route("/health", methods=["GET"])
def health():
    uptime_seconds = time.time() - START_TIME

    avg_time = 0
    if response_times:
        avg_time = sum(response_times) / len(response_times)

    # ✅ Get ChromaDB document count
    try:
        chroma_count = chroma.collection.count()
    except:
        chroma_count = 0

    return jsonify({
        "model": groq.model,
        "avg_response_time": round(avg_time, 3),
        "chroma_docs": chroma_count,
        "uptime_seconds": int(uptime_seconds),
        "cache": {
            "hits": cache_hits,
            "miss": cache_miss,
            "size": len(cache)
        }
    })


if __name__ == "__main__":
    app.run(debug=True)

from flask import Flask
from routes.categorise import categorise_bp

app = Flask(__name__)

# Register route
app.register_blueprint(categorise_bp)

@app.route("/")
def home():
    return {"message": "AI Service Running"}

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
