import {useDispatch, useSelector} from "react-redux";
import {Dispatch, RootState} from "../../state/store";
import axios from "axios";

export const DepositService = () => {
    const auth = useSelector((state: RootState) => state.auth)
    const dispatch = useDispatch<Dispatch>()

    const backendBaseUrl = process.env.REACT_APP_BACKEND_URL

    async function makeDeposit(accountId: number, amount: number) {
        await axios.post(backendBaseUrl + '/account/deposit', {
                accountId: accountId,
                amount: amount,
            },
            {
                headers: {
                    'Authorization': 'Bearer ' + auth?.token
                }
            })
            .then(response => dispatch.lastUpdate.updateAsync(response.data))
            .catch(error => console.log(error.message))
    }

    return {makeDeposit}
}
