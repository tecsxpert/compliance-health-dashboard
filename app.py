from flask import Flask, request, jsonify
from services.chroma_client import ChromaService
from services.groq_client import GroqClient
from services.cache_service import CacheService
import time

app = Flask(__name__)

chroma = ChromaService()
groq = GroqClient()
cache = CacheService()

# tracking
START_TIME = time.time()
response_times = []


# ✅ QUERY API
@app.route("/query", methods=["POST"])
def query():
    start = time.time()

    data = request.json or {}
    question = data.get("question")
    refresh = data.get("refresh", False)

    if not question:
        return jsonify({"error": "question required"}), 400

    # cache check
    if not refresh:
        cached = cache.get(question)
    else:
        cached = None

    if cached:
        answer = cached["answer"]
        docs = cached["sources"]
    else:
        results = chroma.query(question)
        docs = results["documents"][0][:3]

        context = "\n".join(docs)

        prompt = f"""
Answer the question using the below data.

Data:
{context}

Question:
{question}
"""
        answer = groq.generate(prompt)

        cache.set(question, {
            "answer": answer,
            "sources": docs
        })

    # track response time
    response_times.append(time.time() - start)
    if len(response_times) > 10:
        response_times.pop(0)

    return jsonify({
        "answer": answer,
        "sources": docs,
        "cached": cached is not None
    })


# ✅ CATEGORISE API
@app.route("/categorise", methods=["POST"])
def categorise():
    data = request.json or {}
    text = data.get("text")

    if not text:
        return jsonify({"error": "text required"}), 400

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

    answer = groq.generate(prompt, expect_json=True)

    return jsonify(answer)


# ✅ HEALTH API
@app.route("/health", methods=["GET"])
def health():
    uptime = time.time() - START_TIME

    avg_time = (
        sum(response_times) / len(response_times)
        if response_times else 0
    )

    try:
        chroma_count = chroma.collection.count()
    except:
        chroma_count = 0

    return jsonify({
        "status": "ok",
        "model": groq.model,
        "uptime_seconds": int(uptime),
        "avg_response_time": round(avg_time, 3),
        "chroma_docs": chroma_count,
        "cache": cache.stats()
    })


# ✅ HOME
@app.route("/")
def home():
    return {"message": "AI Service Running"}


if __name__ == "__main__":
    app.run(debug=True)