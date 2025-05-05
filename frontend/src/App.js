import './App.css';
import React, {useState} from 'react';
import axios from 'axios';
import { useDropzone } from 'react-dropzone';


function App() {
  const [file, setFile] = useState(null);
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  const { getRootProps, getInputProps } = useDropzone({
    onDrop: (acceptedFiles) => {
      setFile(acceptedFiles[0]);
      setResult(null);
      setError(null);
    },
  });
  const handleUpload = async () => {
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post("http://localhost:8080/api/send-file", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      if (response && response.data) {
        setResult(response.data);
      } else {
        setError("No data in the response.");
      }
    } catch (err) {
      setError("Error while sending: " + err.message);
    }
  };

  return (
    <div className="App">
      <h1>Insignite - File analyzer</h1>
      <div {...getRootProps()} className="dropzone">
        <input {...getInputProps()} />
        <p>Drag and drop your file here, or click to select</p>
      </div>
      { error && <p className="error">{error}</p> }

      { error && <p style={{ color: "red" }}>{error}</p>  }

      { result && (
        <table class="result-table">
          <tbody>
            {Object.entries(result).map(([key, value]) => (
              <tr key={key}>
                <td style={{ fontWeight: "bold", paddingRight: "1rem" }}>{key}</td>
                <td>{String(value)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <button onClick={handleUpload} disabled={!file}>
        {file ? `Upload ${file.name}` : "No file selected"}
      </button>

    </div>
  );
}

export default App;