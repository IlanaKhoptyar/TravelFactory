import './AppCard.css'
import axios from 'axios'
import { saveAs } from 'file-saver';
import { useState } from 'react';
import UploadForm from './UploadForn';
import { Button } from 'react-bootstrap';

function AppCard(props) {

    const appName = props.name
    const appId = props.appId
    const [excelPath, setExcelPath] = useState() 
    const [lastDeployment,setLastDeployment] = useState(props.app.lastDeployment)
    
    function updateLastDeploy(val) {
        if (val) {
            setLastDeployment(val);
            props.app.lastDeployment = val;
        }
       
    }
    
    const downloadExcel = async () => {
        try {
            const response = await axios.get(`/api/apps/${appId}/download`, {
                responseType: 'blob', // Important for binary data
            });
            const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            if (blob.size > 0) {
                const timestamp = Date.now();
                setExcelPath(`${appName}_${timestamp}_translations.xlsx`)
                saveAs(blob, excelPath);
            }
            else {
                alert('No data to download!');
            }
        } catch (error) {
            console.error('There was an error downloading the Excel file!', error);
        }
    };

    return (
        <div className="appCard"> 
            <div className="appCard_inner">

                <div className='app_card_title'> {appName } </div>

                <div className='appUpdateDate'> {lastDeployment ? <span> <b>Last Deployment:</b> {lastDeployment} </span>: ''}</div>

                <div className='app_card_btns'>
                    <Button onClick={() => downloadExcel()} variant="primary" >Download on xslx</Button>
                    <UploadForm updateLastDeploy={updateLastDeploy} appId={props.appId}></UploadForm>
                </div>
            </div>

        </div>
    )
}

export default AppCard