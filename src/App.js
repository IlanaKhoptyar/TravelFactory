import './App.css';
import HomePage from './components/HomePage';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import AppPage from './components/AppPage';

function App() {
  const router = createBrowserRouter([
    { path : '/', element: <HomePage/>},
    { path : '/translator/:name', element: <AppPage/>}
  ])

  return (
    <div className="App">
      
      <RouterProvider router={router}/>
    </div>
  );
}

export default App;
