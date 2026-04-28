import os
import requests
import time
import json
import logging
from dotenv import load_dotenv

load_dotenv()
logging.basicConfig(level=logging.INFO)


class GroqClient:
    def __init__(self, model="llama-3.1-8b-instant"):
        self.api_key = os.getenv("GROQ_API_KEY")

        if not self.api_key:
            raise ValueError("❌ GROQ_API_KEY not found in .env")

        self.model = model
        self.url = "https://api.groq.com/openai/v1/chat/completions"

        self.headers = {
            "Authorization": f"Bearer {self.api_key}",
            "Content-Type": "application/json"
        }

    def generate(self, prompt, expect_json=False, retries=3):
        
        system_msg = (
            "You MUST return valid JSON only. No extra text."
            if expect_json
            else "You are a helpful assistant."
        )

        payload = {
            "model": self.model,
            "messages": [
                {"role": "system", "content": system_msg},
                {"role": "user", "content": prompt}
            ],
            "temperature": 0.3,
            "max_tokens": 512
        }

        for attempt in range(retries):
            try:
                response = requests.post(
                    self.url,
                    headers=self.headers,
                    json=payload,
                    timeout=15
                )

                
                if response.status_code != 200:
                    print(" FULL ERROR:", response.text)
                    raise Exception("Bad API response")

                data = response.json()
                content = data["choices"][0]["message"]["content"]

                logging.info(f"Groq response: {content}")

                
                if expect_json:
                    try:
                        return json.loads(content)
                    except:
                        return {
                            "category": "Unknown",
                            "confidence": 0.0,
                            "reasoning": content
                        }

                # ✅ Normal answer
                return content

            except Exception as e:
                logging.error(f" Attempt {attempt+1} failed: {e}")

                if attempt < retries - 1:
                    time.sleep(2 ** attempt)
                else:
                    return "Error: API failed after retries"