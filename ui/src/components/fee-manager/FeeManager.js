import React, {useState} from "react";
import FeeAddition from './FeeAddition';
import FeesList from "./FeesList";
import Spinner from "react-bootstrap/Spinner";
import Alert from "react-bootstrap/Alert";

const FeeManager = () => {

    const [isLoading, setIsLoading] = useState(true);
    const [isError, setIsError] = useState(false);

    return (
        <>
            <h1>Fee manager</h1>
            <FeeAddition setIsError={setIsError} setIsLoading={setIsLoading}/>
            <hr/>
            {!isLoading && !isError &&
            <FeesList isLoading={isLoading} setIsError={setIsError} setIsLoading={setIsLoading}/>}
            {isLoading && <div className="spinner">
                <Spinner animation="border"/>
            </div>}
            {isError &&
            <Alert variant={'danger'}>
                Can not load list of fees!
            </Alert>}
        </>
    );
};

export default FeeManager;