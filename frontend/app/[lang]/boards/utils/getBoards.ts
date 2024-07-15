import { Board } from '@/app/types/board'
import { PageInfo } from '@/app/types/commons'

export async function getBoards(): Promise<{ data: Board[]; pageInfo: PageInfo }> {
    const res = await fetch(`${process.env.JAVA_BACKEND_URL}/v1/boards`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        cache: 'no-store'
    })

    const data = await res.json()

    return data
}
