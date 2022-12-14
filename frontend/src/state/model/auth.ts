import {createModel} from "@rematch/core";
import {RootModel} from "../index";

export interface AuthState {
    isAuthorized: boolean,
    token: string,
}

const initialState: AuthState = {
    isAuthorized: false,
    token: undefined as unknown as string,
}

export const auth = createModel<RootModel>()({
    state: initialState,
    reducers: {
        update: (state, updatedState: AuthState) => updatedState,
    },
    effects: (dispatch) => ({
        async updateAsync(state: AuthState) {
            dispatch.auth.update(state)
        },
    }),
})
