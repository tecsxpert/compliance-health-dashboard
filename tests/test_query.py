import requests

URL = "http://127.0.0.1:5000/query"

while True:
    question = input("Ask your question (type 'exit' to quit): ")

    if question.lower() in ["exit", "quit"]:
        print("Exiting...")
        break

    try:
        response = requests.post(
            URL,
            json={"question": question}
        )

       
        if response.status_code != 200:
            print(" Error Status:", response.status_code)
            print("Response:", response.text)
            continue

        data = response.json()

        print("\n Answer:", data.get("answer"))
        print(" Sources:", data.get("sources"))
        print(" Cached:", data.get("cached"))
        print("-" * 50)

    except requests.exceptions.RequestException as e:
        print("Request failed:", e)