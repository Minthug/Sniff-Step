import { NextResponse } from 'next/server'

export async function GET(req: Request, { params: { id } }: { params: { id: string } }) {
    const res = await fetch(process.env.NODE_BACKEND_URL + `/boards/${id}/owned`, {
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            authorization: req.headers.get('authorization') || ''
        }
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    const data = await res.json()

    return NextResponse.json({ data }, { status: 200 })
}
