import chromadb
from sentence_transformers import SentenceTransformer


class ChromaService:
    def __init__(self):
        self.client = chromadb.PersistentClient(path="./data/chroma_db")

        self.model = SentenceTransformer("all-MiniLM-L6-v2")

        self.collection = self.client.get_or_create_collection(
            name="health_compliance"
        )

    def add_documents(self, docs):
        embeddings = self.model.encode(docs).tolist()

        ids = [f"id_{i}" for i in range(len(docs))]

        self.collection.add(
            ids=ids,
            documents=docs,
            embeddings=embeddings
        )

    def query(self, text, n=3):
        query_embedding = self.model.encode([text]).tolist()

        results = self.collection.query(
            query_embeddings=query_embedding,
            n_results=n
        )

        return results