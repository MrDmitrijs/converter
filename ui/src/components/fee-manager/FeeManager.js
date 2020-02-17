import React, {useState} from "react";
import FeeAddition from './FeeAddition';
import FeesList from "./FeesList";
import Spinner from "react-bootstrap/Spinner";
import Alert from "react-bootstrap/Alert";

const FeeManager = () => {

    const [isLoading, setIsLoading] = useState(false);
    const [isError, setIsError] = useState(false);

    const [isReloadList, setIsReloadList] = useState(true);

    return (
        <>
            <h1>Fee manager</h1>
            <FeeAddition setIsError={setIsError} setIsLoading={setIsLoading} setIsReloadList={setIsReloadList}/>
            <hr/>
            {!isLoading && !isError &&
            <FeesList isReloadList={isReloadList} setIsError={setIsError} setIsLoading={setIsLoading}
                      setIsReloadList={setIsReloadList}/>}
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