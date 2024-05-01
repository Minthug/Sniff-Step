import { Board } from '@/app/types/board'

export async function getBoards(): Promise<Board[]> {
    const res = await fetch(`${process.env.NODE_BACKEND_URL}/boards`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        cache: 'no-cache'
    })

    const { data } = await res.json()
    return data
}
