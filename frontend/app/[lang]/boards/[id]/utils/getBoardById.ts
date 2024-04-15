import { Board } from '@/app/types/board'

export async function getBoardById(id: string): Promise<Board> {
    const res = await fetch(`http://localhost:3000/api/boards/${id}`, {
        cache: 'no-cache'
    })

    if (res.ok) {
        const { data: board } = await res.json()
        return board
    }

    const {
        data: { message }
    } = await res.json()

    const error = JSON.stringify({ status: res.status, message })

    throw new Error(error)
}
