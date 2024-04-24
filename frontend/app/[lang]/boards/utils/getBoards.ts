import { Board } from '@/app/types/board'

export async function getBoards(): Promise<Board[]> {
    const res = await fetch(`${process.env.NODE_NEXT_BACKEND_URL}/api/boards`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        cache: 'no-cache'
    })

    const { data: boards } = await res.json()
    return boards
}
