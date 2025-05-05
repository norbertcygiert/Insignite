import './App.css';
import React, {useState} from 'react';
import axios from 'axios';
import { useDropzone } from 'react-dropzone';


function App() {
  const [file, setFile] = useState(null);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const onDrop = (acceptedFiles) => {
    setFile(acceptedFiles[0]);
  }

  const {getRootProps, getInputProps} = useDropzone({onDrop, accept: ".txt,.pdf,.md"});

  const handleSubmit = async (e) => {
    if(!file){
      setError("Please select a file");
      return;
    }

    setLoading(true);
    setError("");
    setResult(null);
    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post("http://localhost:8080/api/send-file", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      setResult(response.data);
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="App">
      <h1>Insignite - File analyzer</h1>
      <div {...getRootProps()} className="dropzone">
        <input {...getInputProps()} />
        <p>Drag and drop your file here, or click to select</p>
      </div>
      { error && <p className="error">{error}</p> }

      { loading && <p>Fetching results...</p> }

      { result && ( 
        <div className="result">
          <h2>Result</h2>
          <pre>{JSON.stringify(result, null, 2)}</pre>
        </div>
      )}

      <button onClick={handleSubmit} disabled={loading || !file}>
        {loading ? "Fetching results..." : "Submit"}
      </button>

    </div>
  );
}

export default App;