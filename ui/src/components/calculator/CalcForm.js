import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import axios from 'axios';
import {LIST_OF_CURRENCIES, NUMBER_REGEX} from "../../Constants";

const queryString = require('query-string');

const CalcForm = ({setIsLoading, setIsError, setResult}) => {

    const [calculationForm, setCalculationForm] = useState({
        currencyFrom: 'EUR',
        currencyTo: 'USD',
        amount: '100'
    });

    const [errors, setErrors] = useState({
        amount: false,
        currencyFrom: false,
        currencyTo: false
    });

    const isValidForm = () => {
        const errors = {amount: false, currencyFrom: false, currencyTo: false};
        let isValid = true;
        if (!calculationForm.amount === '' || !NUMBER_REGEX.test(calculationForm.amount)) {
            errors.amount = true;
            isValid = false;
        }
        if(calculationForm.currencyFrom === calculationForm.currencyTo) {
            errors.currencyFrom = true;
            errors.currencyTo = true;
            isValid = false;
        }
        setErrors(errors);
        return isValid;
    };

    const handleSubmit = () => {
        if (isValidForm()) {
            setIsLoading(true);
            setIsError(false);
            axios.get('/calculate?' + queryString.stringify(calculationForm))
                .then(res => {
                    setIsLoading(false);
                    setResult(res + ' ' + calculationForm.currencyTo);
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
                <Form.Group as={Col} controlId="amount">
                    <Form.Label>Amount</Form.Label>
                    <Form.Control
                        required
                        type="number"
                        placeholder="Amount"
                        value={calculationForm.amount}
                        isInvalid={!!errors.amount}
                        onChange={event => {
                            event.persist();
                            setCalculationForm(prevState => ({...prevState, amount: event.target.value}))
                        }}
                    />
                </Form.Group>

                <Form.Group as={Col} controlId="currencyFrom">
                    <Form.Label>From</Form.Label>
                    <Form.Control as="select"
                                  value={calculationForm.currencyFrom}
                                  isInvalid={!!errors.currencyFrom}
                                  onChange={event => {
                                      event.persist();
                                      setCalculationForm(prevState => ({
                                          ...prevState,
                                          currencyFrom: event.target.value
                                      }))
                                  }}>
                        {LIST_OF_CURRENCIES.map(currency => {
                            return <option value={currency}>{currency}</option>
                        })}
                    </Form.Control>
                </Form.Group>

                <Form.Group as={Col} controlId="currencyTo">
                    <Form.Label>To</Form.Label>
                    <Form.Control as="select"
                                  value={calculationForm.currencyTo}
                                  isInvalid={!!errors.currencyTo}
                                  onChange={event => {
                                      event.persist();
                                      setCalculationForm(prevState => ({...prevState, currencyTo: event.target.value}))
                                  }}>
                        {LIST_OF_CURRENCIES.map(currency => {
                            return <option value={currency}>{currency}</option>
                        })}
                    </Form.Control>
                </Form.Group>
            </Form.Row>
            <div className="align-right">
                <Button variant="primary" type="button" onClick={handleSubmit}>
                    Calculate
                </Button>
            </div>
        </Form>
    )
};

export default CalcForm;