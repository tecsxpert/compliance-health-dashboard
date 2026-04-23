from app import app

def test_categorise():
    client = app.test_client()

    response = client.post("/categorise", json={
        "text": "User data is not encrypted"
    })

    assert response.status_code == 200

    data = response.get_json()

    assert "category" in data
    assert "confidence" in data
<<<<<<< HEAD
    assert "reasoning" in data


if __name__ == "__main__":
    test_categorise()
    print("✅ Test Passed")
=======
    assert "reasoning" in data
>>>>>>> d126a5b (Added ChromaDB + RAG pipeline)
