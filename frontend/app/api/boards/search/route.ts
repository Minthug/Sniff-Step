import { NextRequest, NextResponse } from 'next/server'

export async function GET(req: NextRequest) {
    const keyword = req.nextUrl.searchParams.get('keyword')
    const res = await fetch(process.env.NODE_BACKEND_URL + `/boards/search?keyword=${keyword}`, {
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

    return NextResponse.json(data, { status: 200 })
}
