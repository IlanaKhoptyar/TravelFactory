import { Form } from "react-bootstrap";
import { Link, useLocation } from "react-router-dom";
import { Button} from 'react-bootstrap';
import axios from 'axios'
import { useState } from "react";
import './AppPage.css'

function AppPage(props){

    const location = useLocation();
    const app = location.state?.data;

    const [trans, setTrans] = useState('');

    const handleInputChange = (event) => {
        setTrans(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (trans.trim()) {
            try {
                const response = await axios.post(`/api/apps/${app.id}/trans`, { key: trans });
                alert('Translation added successfully!');
                setTrans('');
            } catch (error) {
                console.error('There was an error adding the translation!', error);
            }
        } else {
            alert('Please enter a valid translation');
        }
    };

    return(
        <div className="appPage">
            <Link to='/' className="backLink"> BACK</Link>
            <div className='translationForm'> 
            <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="formTrans">
                            <Form.Label>Translation Adding</Form.Label>
                            <Form.Control
                                type="text"
                                value={trans}
                                onChange={handleInputChange}
                                placeholder="Enter new transaltion key"
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Save
                        </Button>
                    </Form>
            </div>
        </div>
    )
}

export default AppPage;