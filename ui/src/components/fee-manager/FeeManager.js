import React, {useReducer, useState} from "react";
import FeeAddition from './FeeAddition';
import FeesList from "./FeesList";
import Spinner from "react-bootstrap/Spinner";
import Alert from "react-bootstrap/Alert";
import axios from "axios";
import {actions, reducer} from "../../Constants";

const FeeManager = () => {

    const initState = {
        isLoading: false,
        isError: false,
        result: []
    };

    const [state, dispatch] = useReducer(reducer, initState, undefined);
    const [loadListOfFees, setLoadListOfFees] = useState(true);

    if (loadListOfFees) {
        setLoadListOfFees(false);
        dispatch({type: actions.LOADING});
        axios.get('/fees')
            .then(({data}) => {
                dispatch({type: actions.SET_RESULT, payload: data});
            }).catch(() => {
                dispatch({type: actions.ERROR})
            }
        )
    }

    return (
        <>
            <h1>Fee manager</h1>
            <FeeAddition setIsLoading={() => dispatch({type: actions.LOADING})}
                         setIsError={() => dispatch({type: actions.ERROR})}
                         setLoadListOfFees={setLoadListOfFees}/>
            <hr/>
            <FeesList setIsLoading={() => dispatch({type: actions.LOADING})}
                      listOfFees={state.result}
                      setLoadListOfFees={setLoadListOfFees}/>
            {state.isLoading && <div className="spinner">
                <Spinner animation="border"/>
            </div>}
            {state.isError &&
            <Alert variant={'danger'}>
                Can not load list of fees!
            </Alert>}
        </>
    );
};

export default FeeManager;