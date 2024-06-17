import './HomePage.css'

import AppPage from './AppPage';
import { Link } from 'react-router-dom';
import AppCard from './AppCard';
import React, {useEffect, useState} from 'react';
import AddAppModal from './AddAppModal';
import axios from 'axios';

function HomePage() {

    const [apps, setApps] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchApps();
    }, []);

    const fetchApps = async () => {
        try {
            const response = await axios.get('/api/apps');
            setApps(response.data);
            setLoading(false);
        } catch (error) {
            setError('There was an error fetching the applications!');
            setLoading(false);
        }
    };
    
    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    const handleAppAdded = (newApp) => {
        setApps((prevApps) => [...prevApps, newApp]);
    };

    return (
        <div className="homePage"> 
            <div className="homePage_title">Translator manager</div>

            <div className="appsMenu">
                <div className="apps_menu_title">My Apps</div>
                
                <div className="app_arr">
                    {
                        apps.map ((item) => (
                            <Link to= {'/translator/' + item.name} state={{ data: item }} className="appBtn"> {item.name}</Link>
                        ))
                    }
                </div>
                
            </div>
           
            <div className="apps_card_wrapper">
                {
                    apps.map (item => (
                        <AppCard name={item.name} appId={item.id} app={item}></AppCard>
                    ))
                }
                <AddAppModal onAppAdded={handleAppAdded}></AddAppModal>
              
            </div>
        </div>
    )
}

export default HomePage