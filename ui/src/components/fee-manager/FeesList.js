import React from 'react';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import axios from "axios";

const queryString = require('query-string');

const FeesList = ({setIsLoading, listOfFees, setLoadListOfFees}) => {

    const handleRemove = currencyInfo => {
        setIsLoading();
        axios.delete('/fee?' + queryString.stringify(currencyInfo))
            .then(() => {
                setLoadListOfFees(true);
            });
    };

    return (
        listOfFees.map(currencyInfo => {
            return (<Row className="feeRow">
                <Col xs={4}>{currencyInfo.currencyFrom}</Col>
                <Col xs={4}>{currencyInfo.currencyTo}</Col>
                <Col xs={2}>{currencyInfo.fee}</Col>
                <Col xs={2} className="align-right">
                    <Button variant="danger" onClick={() => handleRemove(currencyInfo)}>
                        Remove
                    </Button>
                </Col>
            </Row>)
        })
    );
};

export default FeesList;
