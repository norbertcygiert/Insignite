from flask import Flask, request, jsonify
import magic
import hashlib


app = Flask(__name__)


def is_binary(content: bytes) -> bool:
    """
    Check if the content is binary.
    """
    text_characters = bytearray({7,8,9,10,12,14,27} | set(range(0x20, 0x100)) - {0x7f})
    if not content:
        return False
    _null_trans = bytes.maketrans(b"", b"")
    nontext = content.translate(_null_trans, text_characters)
    return bool(nontext)




@app.route('/analyze', methods=['POST'])

def analyze_file():

    if 'file' not in request.files:
        return jsonify({'error': 'No file part in the request'}), 400
    
    file = request.files['file']
    
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400
    
    file_content = file.read()

    mime_type = magic.from_buffer(file_content, mime=True)

    magic_number = file_content[:8].hex()

    sha256_hash = hashlib.sha256(file_content).hexdigest()

    md5_hash = hashlib.md5(file_content).hexdigest()

    size_bytes = len(file_content)

    is_binary = is_binary(file_content)
    
    result = {
        'filename': file.filename,
        'size_bytes': size_bytes,
        'mime_type': mime_type,
        'is_binary': is_binary,
        'md5': md5_hash,
        'sha256': sha256_hash,
        'magic_number': magic_number
    }

    return jsonify(result), 200

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)