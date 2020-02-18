import React, {useReducer} from "react";
import Spinner from "react-bootstrap/Spinner";
import CalculatorForm from "./CalculatorForm";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Alert from "react-bootstrap/Alert";
import {actions, reducer} from "../../Constants";

const Calculator = () => {

    const initState = {
        isLoading: false,
        isError: false,
        result: ''
    };

    const [state, dispatch] = useReducer(reducer, initState, undefined);

    return (
        <div className="margin-bottom">
            <CalculatorForm setIsLoading={() => dispatch({type: actions.LOADING})}
                            setIsError={() => dispatch({type: actions.ERROR})}
                            setResult={result => dispatch({type: actions.SET_RESULT, payload: result})}/>
            {state.isLoading && <div className="spinner">
                <Spinner animation="border"/>
            </div>}
            {state.isError &&
            <Alert variant={'danger'}>
                Something went wrong! Please try again later!
            </Alert>
            }
            {!state.isLoading && !state.isError &&
            <Row className="justify-content-md-center">
                <Col md="auto">
                    <h3>
                        {state.result}
                    </h3>
                </Col>
            </Row>
            }
        </div>
    )

};

export default Calculator;