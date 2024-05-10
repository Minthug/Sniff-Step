import { NextResponse } from 'next/server'

export async function GET(req: Request, { params: { userId } }: { params: { userId: string } }) {
    console.log(userId)

    const res = await fetch(process.env.NODE_BACKEND_URL + `/auth/refresh/${userId}`, {
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

    const { accessToken } = await res.json()

    return NextResponse.json({ accessToken }, { status: 200 })
}
