import { Board } from '@/app/types/board'

export async function getBoardById(id: string): Promise<Board> {
    const res = await fetch(`${process.env.NODE_BACKEND_URL}/boards/${id}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        cache: 'no-cache'
    })

    if (!res.ok) {
        const { message, statusCode } = await res.json()
        const error = JSON.stringify({ status: statusCode, message })

        throw new Error(error)
    }

    const { data: board } = await res.json()
    return board
}
