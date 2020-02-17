import React, {useState} from "react";
import Spinner from "react-bootstrap/Spinner";
import CalcForm from "./CalcForm";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Alert from "react-bootstrap/Alert";

const Calculator = () => {

    const [isLoading, setIsLoading] = useState(false);
    const [isError, setIsError] = useState(false);
    const [result, setResult] = useState('');

    return (
        <div className="margin-bottom">
            <CalcForm setIsLoading={setIsLoading} setIsError={setIsError} setResult={setResult}/>
            {isLoading && <div className="spinner">
                <Spinner animation="border"/>
            </div>}
            {isError &&
            <Alert variant={'danger'}>
                Something went wrong! Please try again later!
            </Alert>
            }
            {!isLoading && !isError &&
            <Row className="justify-content-md-center">
                <Col md="auto">
                    <h3>
                        {result}
                    </h3>
                </Col>
            </Row>
            }
        </div>
    )

};

export default Calculator;