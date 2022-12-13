import {Block, BlockContent, Button, Flex, FlexItem} from "@qiwi/pijma-desktop";
import React from "react";
import {Navigate, useSearchParams} from "react-router-dom";
import {useDispatch} from "react-redux";
import {Dispatch} from "../../state/store";

export const Login = () => {

    const dispatch = useDispatch<Dispatch>()

    const [searchParams] = useSearchParams()
    const receivedToken = searchParams.get("token")

    console.log(receivedToken)
    console.log(!(!searchParams))
    if (!(!receivedToken)) {
        dispatch.auth.update({
            isAuthorized: true,
            token: receivedToken
        })
        return (<Navigate to={"/"}/>)
    }

    const backendBaseUrl = process.env.REACT_APP_BACKEND_URL

    const onGithubButtonClick = () => {
        document.location.replace(backendBaseUrl + "/login/oauth2/authorization/github")
    };

    return (
        <Flex direction={"column"} justify={"center"} height={"100vh"}>
            <FlexItem width={"100%"}>
                <Flex justify={"space-around"} align={"center"}>
                    <FlexItem>
                        <Block>
                            <BlockContent>
                                <Button kind="brand" type="button" size="accent" text="Github"
                                        onClick={onGithubButtonClick}/>
                            </BlockContent>
                        </Block>
                    </FlexItem>
                </Flex>
            </FlexItem>
        </Flex>
    )
}
