import {Box, Breaker, Section, Typo} from "@qiwi/pijma-desktop";
import React from "react";
import {useSelector} from "react-redux";
import {RootState} from "../../state/store";
import {isPresent} from "../../state/model/lastUpdate";

export const LastUpdate: React.FC = () => {

    const lastUpdate = useSelector((state: RootState) => state.lastUpdate)

    if (isPresent(lastUpdate)) {
        return (
            <Section>
                <Box p={6}>
                    <Typo as="p" display="block" size={4} weight={300}>
                        <Breaker>{'Последнее изменение: cчёт ' + lastUpdate.id +
                            ' баланс ' + lastUpdate.amount + ' ₽ ' +
                            lastUpdate.updated}</Breaker>
                    </Typo>
                </Box>
            </Section>
        )
    }

    return (<div hidden={true}></div>)
}
