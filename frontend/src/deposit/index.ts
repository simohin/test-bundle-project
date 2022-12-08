import {useDispatch} from "react-redux";
import {Dispatch} from "../state/model/store";

export const DepositService = () => {
    const dispatch = useDispatch<Dispatch>()

    const backendBaseUrl = process.env.REACT_APP_BACKEND_URL

    async function makeDeposit(accountId: number, amount: number) {
        await fetch(backendBaseUrl + '/account/deposit', {
            method: 'POST',
            body: JSON.stringify({
                accountId: accountId,
                amount: amount,
            }),
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
        })
            .then((response) => response.json())
            .then((data) => {
                dispatch.lastUpdate.updateAsync(data)
            })
            .catch((err) => {
                console.log(err.message)
            })
    }

    return {makeDeposit}
}
