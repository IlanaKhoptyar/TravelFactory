import React, { useState } from 'react';
import axios from 'axios';
import { Button, Modal, Form } from 'react-bootstrap';

function UploadForm(props) {
    const [file, setFile] = useState(null);
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (!file) {
            alert('Please select a file.');
            return;
        }

       const reader = new FileReader();
        reader.onload = async (e) => {
            const arrayBuffer = e.target.result;
            const bytes = new Uint8Array(arrayBuffer);
         try {
            const response = await axios.post(`/api/apps/${props.appId}/deploy`, bytes, {
                headers: {
                    'Content-Type': 'application/octet-stream'
                }
            });
            const date = response.data
            props.updateLastDeploy(date)
            alert('File uploaded successfully!');
            handleClose();
        } catch (error) {
            console.error('There was an error uploading the file!', error);
            alert('There was an error uploading the file.');
        }
    }
    reader.readAsArrayBuffer(file);
    };

    return (
        <>
            <Button variant="primary" onClick={handleShow} className="addAppBtn">
                Deploy
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Upload Excel</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="fdormAppName">
                            <Form.Label>Upload Excel File:</Form.Label>
                            <Form.Control
                                type="file"
                                onChange={handleFileChange}
                                accept=".xlsx, .xls" 
                                required 
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Upload
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default UploadForm;
