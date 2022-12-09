import React from 'react';
import './App.css';
import {
    Block,
    BlockContent,
    Flex,
    FlexItem,
    fonts,
    Global,
    reset,
    Spacer,
    ThemeProvider,
    themes
} from "@qiwi/pijma-desktop";
import {DepositForm} from "./component/deposit/form";
import {LastUpdate} from "./component/lastUpdate";

const App : React.FC = () => (
    <ThemeProvider theme={themes.orange}>
        <Global styles={[reset, fonts]}/>
        <Flex direction={"column"} justify={"center"} height={"100vh"}>
            <FlexItem width={"100%"}>
                <Flex justify={"space-around"} align={"center"}>
                    <FlexItem>
                        <Block>
                            <BlockContent>
                                <Spacer size="xxl">
                                    <LastUpdate/>
                                    <DepositForm/>
                                </Spacer>
                            </BlockContent>
                        </Block>
                    </FlexItem>
                </Flex>
            </FlexItem>
        </Flex>
    </ThemeProvider>
);

export default App
