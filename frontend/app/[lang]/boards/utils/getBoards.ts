import { Board } from '@/app/types/board'

export async function getBoards(): Promise<Board[]> {
    const res = await fetch('http://localhost:3000/api/boards', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        cache: 'no-cache'
    })

    const { data: boards } = await res.json()
    return boards
}
