import {Models} from '@rematch/core'
import {lastUpdate} from './model/lastUpdate'
import {auth} from "./model/auth";

export interface RootModel extends Models<RootModel> {
    lastUpdate: typeof lastUpdate,
    auth: typeof auth
}

export const models: RootModel = {lastUpdate, auth}
