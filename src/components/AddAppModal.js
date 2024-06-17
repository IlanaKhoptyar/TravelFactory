import { useState } from "react";
import './AddAppModal.css'
import { Button, Modal, Form } from 'react-bootstrap';
import axios from 'axios'

function AddAppModal({onAppAdded}) {
    const [show, setShow] = useState(false);
    const [name, setName] = useState('');

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleInputChange = (event) => {
        setName(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (name.trim()) {
            try {
                const response = await axios.post('/api/apps', { name });
                alert('Application added successfully!');
                setName('');
                onAppAdded(response.data); // Callback to update the parent component
                handleClose();
            } catch (error) {
                console.error('There was an error adding the application!', error);
            }
        } else {
            alert('Please enter a valid application name');
        }
    };
   
    return (
        <>
            <Button variant="primary" onClick={handleShow} className="addAppBtn">
                Add App
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add New Application</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="formAppName">
                            <Form.Label>Application Name</Form.Label>
                            <Form.Control
                                type="text"
                                value={name}
                                onChange={handleInputChange}
                                placeholder="Enter application name"
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Save
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default AddAppModal;