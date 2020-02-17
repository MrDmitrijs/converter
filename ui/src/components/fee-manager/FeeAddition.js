import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import {LIST_OF_CURRENCIES, NUMBER_REGEX} from "../../Constants";
import axios from "axios";

const FeeAddition = ({setIsError, setIsLoading}) => {

    const [feeAdditionForm, setFeeAdditionForm] = useState({
        currencyFrom: 'EUR',
        currencyTo: 'USD',
        fee: '0.05'
    });

    const [errors, setErrors] = useState({
        fee: false,
        currencyFrom: false,
        currencyTo: false
    });

    const isValidForm = () => {
        const errors = {fee: false, currencyFrom: false, currencyTo: false};
        let isValid = true;
        if (!feeAdditionForm.fee === '' || !NUMBER_REGEX.test(feeAdditionForm.fee)) {
            errors.fee = true;
            isValid = false;
        }
        if(feeAdditionForm.currencyFrom === feeAdditionForm.currencyTo) {
            errors.currencyFrom = true;
            errors.currencyTo = true;
            isValid = false;
        }
        setErrors(errors);
        return isValid;
    };

    const handleSubmit = () => {
        if (isValidForm()) {
            setIsError(false);
            axios.post('/fee', feeAdditionForm)
                .then(() => {
                    setIsLoading(true);
                })
                .catch(() => {
                    setIsLoading(false);
                    setIsError(true);
                })
        }
    };

    return (
        <Form noValidate onSubmit={handleSubmit}>
            <Form.Row>
                <Form.Group as={Col} controlId="currencyFrom">
                    <Form.Label>From</Form.Label>
                    <Form.Control as="select"
                                  value={feeAdditionForm.currencyFrom}
                                  isInvalid={!!errors.currencyFrom}
                                  onChange={event => {
                        event.persist();
                        setFeeAdditionForm(prevState => ({...prevState, currencyFrom: event.target.value}))
                    }}>
                        {LIST_OF_CURRENCIES.map(currency => {
                            return <option value={currency}>{currency}</option>
                        })}
                    </Form.Control>
                </Form.Group>
                <Form.Group as={Col} controlId="currencyTo">
                    <Form.Label>To</Form.Label>
                    <Form.Control as="select"
                                  value={feeAdditionForm.currencyTo}
                                  isInvalid={!!errors.currencyTo}
                                  onChange={event => {
                        event.persist();
                        setFeeAdditionForm(prevState => ({...prevState, currencyTo: event.target.value}))
                    }}>
                        {LIST_OF_CURRENCIES.map(currency => {
                            return <option value={currency}>{currency}</option>
                        })}
                    </Form.Control>
                </Form.Group>
                <Form.Group as={Col} controlId="fee">
                    <Form.Label>Fee</Form.Label>
                    <Form.Control
                        type="number"
                        isInvalid={!!errors.fee}
                        placeholder="Fee"
                        value={feeAdditionForm.fee}
                        onChange={event => {
                            event.persist();
                            setFeeAdditionForm(prevState => ({...prevState, fee: event.target.fee}))
                        }}
                    />
                </Form.Group>
                <Button variant="primary" type="button" className="addBtn" onClick={handleSubmit}>
                    Add
                </Button>
            </Form.Row>
        </Form>
    )
};

export default FeeAddition;