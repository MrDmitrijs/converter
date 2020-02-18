export const LIST_OF_CURRENCIES = ["CAD","HKD","ISK","PHP","DKK","HUF","CZK","AUD","RON","SEK","IDR","INR","BRL","RUB","HRK","JPY","THB","CHF","SGD","PLN","BGN","TRY","CNY","NOK","NZD","ZAR","USD","MXN","ILS","GBP","KRW","MYR","EUR"];

export const NUMBER_REGEX = /^[0-9]\d*(\.\d+)?$/;

export const actions = {
    LOADING: 'LOADING',
    ERROR: 'ERROR',
    SET_RESULT: 'SET_RESULT'
};

export const reducer = (state, action) => {
    switch (action.type) {
        case actions.LOADING:
            return {...state, isLoading: true, isError: false};
        case actions.ERROR:
            return {...state, isLoading: false, isError: true};
        case actions.SET_RESULT:
            return {isLoading: false, isError: false, result: action.payload};
        default:
            return {...state, isLoading: false, isError: true};
    }
};