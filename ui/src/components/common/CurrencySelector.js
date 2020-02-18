import React from "react";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import {LIST_OF_CURRENCIES} from "../../Constants";

const CurrencySelector = ({value, error, handleOnChange, id, label}) => {

    return (
        <Form.Group as={Col} controlId={id}>
            <Form.Label>{label}</Form.Label>
            <Form.Control as="select"
                          value={value}
                          isInvalid={!!error}
                          onChange={handleOnChange}>
                {LIST_OF_CURRENCIES.map((currency, index) => {
                    return <option key={index} value={currency}>{currency}</option>
                })}
            </Form.Control>
        </Form.Group>
    )
};

export default CurrencySelector;