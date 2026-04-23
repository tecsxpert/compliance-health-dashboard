import os
import requests
<<<<<<< HEAD
from dotenv import load_dotenv

load_dotenv()
=======
import time
import logging
import json
from dotenv import load_dotenv

load_dotenv()
logging.basicConfig(level=logging.INFO)
>>>>>>> d126a5b (Added ChromaDB + RAG pipeline)

class GroqClient:
    def __init__(self, model="llama3-8b-8192"):
        self.api_key = os.getenv("GROQ_API_KEY")

        if not self.api_key:
            raise ValueError("❌ GROQ_API_KEY not found in .env")

        self.model = model
        self.url = "https://api.groq.com/openai/v1/chat/completions"
<<<<<<< HEAD
=======

        self.headers = {
            "Authorization": f"Bearer {self.api_key}",
            "Content-Type": "application/json"
        }
>>>>>>> d126a5b (Added ChromaDB + RAG pipeline)

    def generate(self, prompt, retries=3):
        payload = {
            "model": self.model,
            "messages": [
                {
<<<<<<< HEAD
=======
                    "role": "system",
                    "content": "You MUST return valid JSON only. No extra text."
                },
                {
>>>>>>> d126a5b (Added ChromaDB + RAG pipeline)
                    "role": "user",
                    "content": prompt
                }
            ]
        }

<<<<<<< HEAD
        try:
            response = requests.post(
                self.url,
                headers={
                    "Authorization": f"Bearer {self.api_key}",
                    "Content-Type": "application/json"
                },
                json=payload,
                timeout=10
            )

            # 🔥 PRINT FULL ERROR (IMPORTANT)
            if response.status_code != 200:
                print("❌ FULL ERROR:", response.text)
                return "Error: Unable to generate answer"

            data = response.json()

            return data["choices"][0]["message"]["content"]

        except Exception as e:
            print("❌ Exception:", e)
            return "Error: Unable to generate answer"
=======
        for attempt in range(retries):
            try:
                response = requests.post(
                    self.url,
                    headers=self.headers,
                    json=payload,
                    timeout=15
                )

                response.raise_for_status()
                data = response.json()

                content = data["choices"][0]["message"]["content"]

                logging.info(f"Groq raw response: {content}")

                try:
                    return json.loads(content)
                except json.JSONDecodeError:
                    return {
                        "category": "Unknown",
                        "confidence": 0.0,
                        "reasoning": "Invalid JSON from model"
                    }

            except requests.exceptions.RequestException as e:
                logging.error(f"Attempt {attempt + 1} failed: {e}")

                if attempt < retries - 1:
                    time.sleep(2 ** attempt)
                else:
                    return {
                        "category": "Error",
                        "confidence": 0.0,
                        "reasoning": "API failed after retries"
                    }
>>>>>>> d126a5b (Added ChromaDB + RAG pipeline)
