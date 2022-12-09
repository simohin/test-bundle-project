import {useDispatch} from "react-redux";
import {Dispatch} from "../../state/store";
import axios from "axios";

export const DepositService = () => {
    const dispatch = useDispatch<Dispatch>()

    const backendBaseUrl = process.env.REACT_APP_BACKEND_URL

    async function makeDeposit(accountId: number, amount: number) {
        await axios.post(backendBaseUrl + '/account/deposit', {
            accountId: accountId,
            amount: amount,
        })
            .then(response => dispatch.lastUpdate.updateAsync(response.data))
            .catch(error => console.log(error.message))
    }

    return {makeDeposit}
}
