import {Button, createNumberMask, Form, TextField} from "@qiwi/pijma-desktop";
import React from "react";
import {DepositService} from "../../../service/deposit";

export const DepositForm: React.FC = () => {

    const spaces = /\s/g;

    const [amount, setAmount] = React.useState("0")
    const [accountId, setAccountId] = React.useState("0")

    const {makeDeposit} = DepositService()

    const onFormSubmit = () => {
        makeDeposit(
            parseInt(accountId.replace(spaces, '')),
            parseFloat(amount.replace(spaces, ''))
        ).then(() => {
        })
    };

    return (
        <Form>
            <TextField
                title="Номер счёта"
                type="tel"
                mask={createNumberMask({
                    decimalLimit: 0,
                    requireDecimal: false,
                    integerLimit: 20,
                    includeThousandsSeparator: false
                })}
                value={accountId}
                onChange={(text) => setAccountId(text)}
            />
            <TextField
                title="Сумма"
                type="tel"
                mask={createNumberMask({
                    suffix: ' ₽',
                    decimalLimit: 2,
                    requireDecimal: true,
                    integerLimit: 20,
                })}
                value={amount}
                onChange={(text) => setAmount(text)}
            />
            <Button kind="brand" type="submit" size="accent" text="Пополнить"
                    onClick={onFormSubmit}/>
        </Form>
    )
}
