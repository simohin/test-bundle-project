import {createModel} from '@rematch/core'
import {RootModel} from "../index";

export interface LastUpdateState {
    id: number,
    amount: number,
    updated: string,
}

export const isPresent = (state: LastUpdateState) => {
    return state.id !== undefined && state.amount !== undefined && state.updated !== undefined
}

const initialState: LastUpdateState = {
    id: undefined as unknown as number,
    amount: undefined as unknown as number,
    updated: undefined as unknown as string
}

export const lastUpdate = createModel<RootModel>()({
    state: initialState,
    reducers: {
        update: (state, updatedState: LastUpdateState) => updatedState,
    },
    effects: (dispatch) => ({
        async updateAsync(state: LastUpdateState) {
            dispatch.lastUpdate.update(state)
        },
    }),
})
