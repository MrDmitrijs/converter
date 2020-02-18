import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import axios from 'axios';
import {NUMBER_REGEX} from "../../Constants";
import CurrencySelector from "../common/CurrencySelector";
import NumberField from "../common/NumberField";

const queryString = require('query-string');

const CalculatorForm = ({setIsLoading, setIsError, setResult}) => {

    const [calculationForm, setCalculationForm] = useState({
        currencyFrom: 'EUR',
        currencyTo: 'USD',
        amount: '100'
    });

    const [formErrors, setFormErrors] = useState({
        amount: false,
        currencyFrom: false,
        currencyTo: false
    });

    const isValidForm = () => {
        const errors = {amount: false, currencyFrom: false, currencyTo: false};
        let isValid = true;
        if (!calculationForm.amount || !NUMBER_REGEX.test(calculationForm.amount)) {
            errors.amount = true;
            isValid = false;
        }
        if (calculationForm.currencyFrom === calculationForm.currencyTo) {
            errors.currencyFrom = true;
            errors.currencyTo = true;
            isValid = false;
        }
        setFormErrors(errors);
        return isValid;
    };

    const handleSubmit = () => {
        if (isValidForm()) {
            setIsLoading();
            axios.get('/calculate?' + queryString.stringify(calculationForm))
                .then(({data}) => {
                    setResult(data + ' ' + calculationForm.currencyTo);
                })
                .catch(() => {
                    setIsError();
                })
        }
    };

    const handleOnChange = event => {
        event.persist();
        let value = event.target.value;
        const id = event.target.id;
        switch (id) {
            case "amount":
                if (value <= 0) value = 1;
                return setCalculationForm(prevState => ({...prevState, amount: value}));
            case "currencyFrom":
                return setCalculationForm(prevState => ({...prevState, currencyFrom: value}));
            case "currencyTo":
                return setCalculationForm(prevState => ({...prevState, currencyTo: value}));
            default:
                setIsError();
        }
    };

    return (
        <Form noValidate onSubmit={handleSubmit}>
            <Form.Row>
                <NumberField error={formErrors.amount}
                             handleOnChange={handleOnChange}
                             value={calculationForm.amount}
                             id={"amount"}
                             label={"Amount"}/>

                <CurrencySelector error={formErrors.currencyFrom}
                                  handleOnChange={handleOnChange}
                                  value={calculationForm.currencyFrom}
                                  id={"currencyFrom"}
                                  label={"From"}/>

                <CurrencySelector error={formErrors.currencyTo}
                                  handleOnChange={handleOnChange}
                                  value={calculationForm.currencyTo}
                                  id={"currencyTo"}
                                  label={"To"}/>
            </Form.Row>
            <div className="align-right">
                <Button variant="primary" type="button" onClick={handleSubmit}>
                    Calculate
                </Button>
            </div>
        </Form>
    )
};

export default CalculatorForm;