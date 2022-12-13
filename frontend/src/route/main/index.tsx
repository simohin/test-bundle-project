import {Block, BlockContent, Flex, FlexItem, Spacer} from "@qiwi/pijma-desktop";
import {LastUpdate} from "../../component/lastUpdate";
import {DepositForm} from "../../component/deposit/form";
import React from "react";
import {useSelector} from "react-redux";
import {RootState} from "../../state/store";
import {Navigate} from "react-router-dom";


export const Main = () => {
    const auth = useSelector((state: RootState) => state.auth)

    if (!auth.isAuthorized) {
        return (<Navigate to={"/login"}/>)
    }

    return (
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
    )
}
