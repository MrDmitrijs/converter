import React from "react";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";

const NumberField = ({value, error, handleOnChange, id, label}) => {

    return (
        <Form.Group as={Col} controlId={id}>
            <Form.Label>{label}</Form.Label>
            <Form.Control
                type="number"
                placeholder="Amount"
                value={value}
                isInvalid={!!error}
                onChange={handleOnChange}
            />
        </Form.Group>
    )
};

export default NumberField;