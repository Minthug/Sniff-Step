import { NextRequest, NextResponse } from 'next/server'

export async function GET(req: NextRequest) {
    const res = await fetch(process.env.NODE_BACKEND_URL + `/auth/profile`, {
        method: 'GET',
        headers: {
            authorization: req.headers.get('authorization') || ''
        }
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    const data = await res.json()
    return NextResponse.json({ message: 'success', data }, { status: 200 })
}
