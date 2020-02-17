import React, {useState} from 'react';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import axios from "axios";

const queryString = require('query-string');

const FeesList = ({isLoading, setIsError, setIsLoading}) => {

    const [listOfFees, setListOfFees] = useState([]);

    if (isLoading) {
        setIsError(false);
        axios.get('/fees')
            .then(res => {
                setListOfFees(res);
                setIsLoading(false);
            }).catch(() => {
                setIsLoading(false);
                setIsError(true);
            }
        )
    }

    const handleRemove = currencyInfo => {
        axios.delete('/fee' + queryString.stringify(currencyInfo))
            .then(() => {
                setIsLoading(true);
            });
    };

    return (
        listOfFees.map(currencyInfo => {
            return <>
                <Row>
                    <Col xs={4}>currencyInfo.currencyFrom</Col>
                    <Col xs={4}>currencyInfo.currencyTo</Col>
                    <Col xs={2}>currencyInfo.fee</Col>
                    <Col xs={2} className="align-right">
                        <Button variant="danger" onClick={handleRemove(currencyInfo)}>
                            Remove
                        </Button>
                    </Col>
                </Row>
            </>
        })
    );
};

export default FeesList;
