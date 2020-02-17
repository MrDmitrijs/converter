import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from './components/header'
import Calculator from './components/calculator';
import FeeManager from './components/fee-manager';

function App() {
    return (
        <>
            <Header/>
            <div id="currency-converter">
                <Calculator/>
                <FeeManager/>
            </div>
        </>
    );
}

export default App;
