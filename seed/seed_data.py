
import json
import chromadb
from sentence_transformers import SentenceTransformer

# 🔹 Load external JSON file
with open("data/health_data.json", "r") as f:
    data = json.load(f)

# 🔹 Init Chroma
client = chromadb.PersistentClient(path="./data/chroma_db")

collection = client.get_or_create_collection(
    name="health_compliance"
)

model = SentenceTransformer("all-MiniLM-L6-v2")

documents = []
metadatas = []
ids = []

# 🔹 Process data
for item in data:
    text = f"{item['title']}: {item['content']}"

    documents.append(text)
    metadatas.append({
        "category": item["category"],
        "title": item["title"]
    })
    ids.append(item["id"])

# 🔹 Create embeddings
embeddings = model.encode(documents).tolist()

# 🔹 Insert into DB
collection.add(
    documents=documents,
    embeddings=embeddings,
    metadatas=metadatas,
    ids=ids
)

print("✅ External JSON data inserted into ChromaDB!")

from services.chroma_client import ChromaService

chroma = ChromaService()

documents = [
    # Security
    "Sensitive user data must be encrypted using AES-256 encryption.",
    "Passwords should be hashed using bcrypt before storing in database.",
    "Unauthorized access to admin panel is a critical security violation.",

    # Compliance
    "All user activities must be logged for audit compliance.",
    "System must follow GDPR rules for handling personal data.",
    "Compliance requires proper documentation of all transactions.",

    # Risk
    "Missing encryption leads to high security risk.",
    "Lack of monitoring increases operational risk.",
    "Unpatched systems may lead to cyber attacks.",

    # Performance
    "Slow database queries reduce system performance.",
    "Caching frequently accessed data improves response time.",
    "High latency affects user experience in dashboards.",

    # Operations
    "Daily backups are required for system reliability.",
    "System uptime should be at least 99.9 percent.",
    "Monitoring tools should track system health continuously."
]

chroma.add_documents(documents)

print("✅ ChromaDB seeded successfully")

