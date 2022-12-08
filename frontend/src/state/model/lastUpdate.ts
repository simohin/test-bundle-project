import {createModel} from '@rematch/core'
import type {RootModel} from '.'

export interface LastUpdateState {
    id: number,
    amount: number,
    updated: string,
}

const initialState: LastUpdateState = {
    id: 0,
    amount: 0,
    updated: ''
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
