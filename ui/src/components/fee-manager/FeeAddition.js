import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {NUMBER_REGEX} from "../../Constants";
import axios from "axios";
import CurrencySelector from "../common/CurrencySelector";
import NumberField from "../common/NumberField";

const FeeAddition = ({setIsLoading, setIsError, setLoadListOfFees}) => {

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
        if (!feeAdditionForm.fee || !NUMBER_REGEX.test(feeAdditionForm.fee)) {
            errors.fee = true;
            isValid = false;
        }
        if (feeAdditionForm.currencyFrom === feeAdditionForm.currencyTo) {
            errors.currencyFrom = true;
            errors.currencyTo = true;
            isValid = false;
        }
        setErrors(errors);
        return isValid;
    };

    const handleSubmit = () => {
        if (isValidForm()) {
            axios.post('/fee', feeAdditionForm)
                .then(() => {
                    setIsLoading();
                    setLoadListOfFees(true);
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
            case "fee":
                if (value <= 0) value = 0.01;
                if (value > 1) value = 0.99;
                return setFeeAdditionForm(prevState => ({...prevState, fee: value}));
            case "currencyFrom":
                return setFeeAdditionForm(prevState => ({...prevState, currencyFrom: value}));
            case "currencyTo":
                return setFeeAdditionForm(prevState => ({...prevState, currencyTo: value}));
            default:
                setIsError();
        }
    };

    return (
        <Form noValidate onSubmit={handleSubmit}>
            <Form.Row>
                <CurrencySelector error={errors.currencyFrom}
                                  handleOnChange={handleOnChange}
                                  value={feeAdditionForm.currencyFrom}
                                  id={"currencyFrom"}
                                  label={"From"}/>

                <CurrencySelector error={errors.currencyTo}
                                  handleOnChange={handleOnChange}
                                  value={feeAdditionForm.currencyTo}
                                  id={"currencyTo"}
                                  label={"To"}/>

                <NumberField error={errors.fee}
                             handleOnChange={handleOnChange}
                             value={feeAdditionForm.fee}
                             id={"fee"}
                             label={"Fee"}/>

                <Button variant="primary" type="button" className="addBtn" onClick={handleSubmit}>
                    Add
                </Button>
            </Form.Row>
        </Form>
    )
};

export default FeeAddition;