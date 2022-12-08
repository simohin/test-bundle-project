import React from 'react';
import './App.css';
import {
    Block,
    BlockContent,
    Box,
    Breaker,
    Button,
    createNumberMask,
    Flex,
    FlexItem,
    fonts,
    Form,
    Global,
    reset,
    Section,
    Spacer,
    TextField,
    ThemeProvider,
    themes,
    Typo
} from "@qiwi/pijma-desktop";
import {useSelector} from 'react-redux'
import {RootState} from "./state/model/store";
import {DepositService} from "./deposit";

function App() {

    const [amount, setAmount] = React.useState("0")
    const [accountId, setAccountId] = React.useState("1")

    const lastUpdate = useSelector((state: RootState) => state.lastUpdate)
    const {makeDeposit} = DepositService()

    function onFormSubmit() {
        const spaces = /\s/g;
        makeDeposit(
            parseInt(accountId.replace(spaces, '')),
            parseFloat(amount.replace(spaces, ''))
        )
    }

    return (
        <ThemeProvider theme={themes.orange}>
            <Global styles={[reset, fonts]}/>
            <Flex direction={"column"} justify={"center"} height={"100vh"}>
                <FlexItem width={"100%"}>
                    <Flex justify={"space-around"} align={"center"}>
                        <FlexItem>
                            <Block>
                                <BlockContent>
                                    <Spacer size="xxl">
                                        <Section>
                                            <Box p={6}>
                                                <Typo as="p" display="block" size={4} weight={300}>
                                                    <Breaker>{'Последнее изменение: cчёт ' + lastUpdate.id +
                                                        ' баланс ' + lastUpdate.amount + ' ₽ ' +
                                                        lastUpdate.updated}</Breaker>
                                                </Typo>
                                            </Box>
                                        </Section>
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
                                    </Spacer>
                                </BlockContent>
                            </Block>
                        </FlexItem>
                    </Flex>
                </FlexItem>
            </Flex>
        </ThemeProvider>
    );
}

export default App
