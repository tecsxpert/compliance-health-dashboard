from services.chroma_client import ChromaService


def test_chroma():
    chroma = ChromaService()

    result = chroma.query("data encryption issue")

    doc = result["documents"][0][0]

    assert "encrypt" in doc.lower()

    print(" Test Passed")
    print("Top Result:", doc)


if __name__ == "__main__":
    test_chroma()