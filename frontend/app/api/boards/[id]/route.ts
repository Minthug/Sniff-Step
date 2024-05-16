import { NextResponse } from 'next/server'

export async function DELETE(req: Request, { params: { id } }: { params: { id: string } }) {
    const res = await fetch(process.env.NODE_BACKEND_URL + `/boards/${id}`, {
        method: 'DELETE',
        headers: {
            'content-type': 'application/json',
            authorization: req.headers.get('authorization') || ''
        }
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    return NextResponse.json({}, { status: 200 })
}